package com.caetp.digiex.service;

import com.caetp.digiex.consts.RedisKeyParams;
import com.caetp.digiex.consts.StaticProperties;
import com.caetp.digiex.dto.PageInfoDTO;
import com.caetp.digiex.dto.TPage;
import com.caetp.digiex.dto.api.MarketDTO;
import com.caetp.digiex.entity.Market;
import com.caetp.digiex.entity.MarketCollect;
import com.caetp.digiex.entity.Member;
import com.caetp.digiex.entity.mapper.MarketCollectMapper;
import com.caetp.digiex.entity.mapper.MarketMapper;
import com.caetp.digiex.entity.mapper.MemberMapper;
import com.caetp.digiex.exception.CommonException;
import com.caetp.digiex.redis.RedisBaseDao;
import com.caetp.digiex.utli.market.MarketUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangzy on 2019/2/24.
 */
@Service
public class MarketService extends BaseService {

    @Autowired
    private RedisBaseDao<String, Map<String, Double>> redisBaseDao;

    @Autowired
    private MarketMapper marketMapper;
    @Autowired
    private MarketCollectMapper marketCollectMapper;
    @Autowired
    private MemberMapper memberMapper;

    private static final String MARKET_REAL_DATA = "market_real_data";
    private static final String MARKET_OLD_DATA = "market_old_data";


    /**
     * 行情列表
     * @param token
     * @param type
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public TPage<MarketDTO> list(String token, String type, Integer pageNumber, Integer pageSize) {
        Integer memberIdTemp = 0;
        if (!StringUtils.isEmpty(token)) {
            List<Member> members = memberMapper.listUserByToken(token);
            if (!members.isEmpty()) {
                memberIdTemp = members.get(0).getMemberId();
            }
        }
        Integer memberId = memberIdTemp;
        List<MarketDTO> list = PageHelper.startPage(pageNumber,pageSize).doSelectPage(() ->
                marketMapper.list(memberId, type));
        list = getPriceAndRate(list);
        PageInfo<MarketDTO> pageInfo = new PageInfo<>(list);
        PageInfoDTO pageInfoDTO = new PageInfoDTO((int) pageInfo.getTotal(), pageNumber, pageInfo.getPages(), pageSize);
        return TPage.of(list, pageInfo.getTotal(), pageInfoDTO);
    }

    /**
     * 获取price，rate相关信息
     * @param list
     * @return
     */
    private List<MarketDTO> getPriceAndRate(List<MarketDTO> list) {
        String pairs = marketMapper.getCoinsString("fx");
        Map<String, Double> realDataCache = redisBaseDao.redisRead(MARKET_REAL_DATA);
        Map<String, Double> oldDataCache = redisBaseDao.redisRead(MARKET_OLD_DATA);
        if (realDataCache == null) {
            realDataCache = MarketUtils.getRealInfo(pairs);
            if (realDataCache == null) {
                throw  CommonException.NOT_FOUND_RESULT;
            } else {
                // 更新real data
                redisBaseDao.redisSaveSerializableExpire(MARKET_REAL_DATA, realDataCache,
                        RedisKeyParams.MARKET_REAL_DATA, TimeUnit.SECONDS);
                // 更新old data
                redisBaseDao.redisSaveSerializable(MARKET_OLD_DATA, realDataCache);
            }
        }
        if (oldDataCache == null) {
            oldDataCache = realDataCache;
        }
        for (MarketDTO dto : list) {
            Double realPrice = realDataCache.get(dto.getCoin());
            Double oldPrice = oldDataCache.get(dto.getCoin());
            if (oldPrice != null && realPrice != null) {
                dto.setRate(BigDecimal.valueOf(realPrice - oldPrice).divide(BigDecimal.valueOf(oldPrice), 4, BigDecimal.ROUND_DOWN)
                        .multiply(BigDecimal.valueOf(100)).doubleValue());
            }
            dto.setPrice(realPrice == null ? 0.0 : realPrice);
            if ("fx".equals(dto.getCoinType())) {
                dto.setDetailUrl(StaticProperties.MARKET_DETAIL_URL + dto.getCoin());
            }
        }
        return list;
    }

    /**
     * 自选列表
     * @param token
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public TPage<MarketDTO> collectList(String token, Integer pageNumber, Integer pageSize) {
        Integer memberIdTemp = 0;
        if (!StringUtils.isEmpty(token)) {
            List<Member> members = memberMapper.listUserByToken(token);
            if (!members.isEmpty()) {
                memberIdTemp = members.get(0).getMemberId();
            }
        }
        Integer memberId = memberIdTemp;
        if (memberId == 0) {
            return TPage.of(new ArrayList<>(), 0);
        }
        List<MarketDTO> list = PageHelper.startPage(pageNumber,pageSize).doSelectPage(() ->
                marketMapper.collectList(memberId));
        list = getPriceAndRate(list);
        PageInfo<MarketDTO> pageInfo = new PageInfo<>(list);
        PageInfoDTO pageInfoDTO = new PageInfoDTO((int) pageInfo.getTotal(), pageNumber, pageInfo.getPages(), pageSize);
        return TPage.of(list, pageInfo.getTotal(), pageInfoDTO);
    }

    /**
     * 收藏
     * @param token
     * @param id
     */
    public void collect(String token, Long id) {
        Member member = validateUserToken(token);
        MarketCollect marketCollect = marketCollectMapper.selectByPrimaryKey(id, member.getMemberId());
        if (marketCollect == null) {
            marketCollectMapper.insert(MarketCollect.builder()
                    .marketId(id).memberId(member.getMemberId()).build());
        }

    }

    /**
     * 取消收藏
     * @param token
     * @param id
     */
    public void cancelCollect(String token, Long id) {
        Member member = validateUserToken(token);
        marketCollectMapper.deleteByPrimaryKey(id, member.getMemberId());
    }

    /** 搜索货币
     * @param token
     * @param keyword 关键字
     * @param pageNumber 页数
     * @param pageSize 每一页大小
     * @return
     */
    public TPage<MarketDTO> searchCollect(String token, String keyword, Integer pageNumber, Integer pageSize) {
        Integer memberIdTemp = 0;
        if (!StringUtils.isEmpty(token)) {
            List<Member> members = memberMapper.listUserByToken(token);
            if (!members.isEmpty()) {
                memberIdTemp = members.get(0).getMemberId();
            }
        }
        Integer memberId = memberIdTemp;
        List<MarketDTO> list = PageHelper.startPage(pageNumber, pageSize).doSelectPage(() ->
                marketMapper.searchList(keyword));
        //实时价格 和 价格涨幅
        list = getPriceAndRate(list);
        PageInfo<MarketDTO> pageInfo = new PageInfo<>(list);
        PageInfoDTO pageInfoDTO = new PageInfoDTO((int) pageInfo.getTotal(), pageNumber, pageInfo.getPages(), pageSize);
        return TPage.of(list, pageInfo.getTotal(), pageInfoDTO);
    }


}
