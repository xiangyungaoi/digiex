package com.caetp.digiex.config;

import com.caetp.digiex.utli.common.SpringContextUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Stack;

/**
 * 自定义事务AOP
 * Created by wangzy on 2019/5/7.
 */
@Aspect
@Configuration
public class TransactionAop {

    @Pointcut("@annotation(CustomTransaction)")
    public void CustomTransaction() {
    }

    @Around(value = "CustomTransaction()&&@annotation(annotation)")
    public Object twiceAsOld(ProceedingJoinPoint thisJoinPoint, CustomTransaction annotation) throws Throwable {
        Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack = new Stack<DataSourceTransactionManager>();
        Stack<TransactionStatus> transactionStatuStack = new Stack<TransactionStatus>();

        try {

            if (!openTransaction(dataSourceTransactionManagerStack, transactionStatuStack, annotation)) {
                return null;
            }

            Object ret = thisJoinPoint.proceed();

            commit(dataSourceTransactionManagerStack, transactionStatuStack);

            return ret;
        } catch (Throwable e) {
            rollback(dataSourceTransactionManagerStack, transactionStatuStack);
            throw e;
        }
    }

    /**
     * 开启事务处理方法
     *
     * @param dataSourceTransactionManagerStack
     * @param transactionStatuStack
     * @param multiTransactional
     * @return
     */
    private boolean openTransaction(Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack,
                                    Stack<TransactionStatus> transactionStatuStack, CustomTransaction multiTransactional) {

        String[] transactionMangerNames = multiTransactional.value();
        if (multiTransactional.value().length == 0) {
            return false;
        }

        for (String beanName : transactionMangerNames) {
            DataSourceTransactionManager dataSourceTransactionManager = SpringContextUtil.getBean(beanName);
            TransactionStatus transactionStatus = dataSourceTransactionManager
                    .getTransaction(new DefaultTransactionDefinition());
            transactionStatuStack.push(transactionStatus);
            dataSourceTransactionManagerStack.push(dataSourceTransactionManager);
        }
        return true;
    }

    /**
     * 提交处理方法
     *
     * @param dataSourceTransactionManagerStack
     * @param transactionStatuStack
     */
    private void commit(Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack,
                        Stack<TransactionStatus> transactionStatuStack) {
        while (!dataSourceTransactionManagerStack.isEmpty()) {
            dataSourceTransactionManagerStack.pop().commit(transactionStatuStack.pop());
        }
    }

    /**
     * 回滚处理方法
     *
     * @param dataSourceTransactionManagerStack
     * @param transactionStatuStack
     */
    private void rollback(Stack<DataSourceTransactionManager> dataSourceTransactionManagerStack,
                          Stack<TransactionStatus> transactionStatuStack) {
        while (!dataSourceTransactionManagerStack.isEmpty()) {
            dataSourceTransactionManagerStack.pop().rollback(transactionStatuStack.pop());
        }
    }
}