package com.xiaojun.yaodiandemo.beans;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.xiaojun.yaodiandemo.beans.BaoCunBean;
import com.xiaojun.yaodiandemo.beans.BenDiShenFenZhen;
import com.xiaojun.yaodiandemo.beans.TianJiaYao;
import com.xiaojun.yaodiandemo.beans.UserInfoBena;

import com.xiaojun.yaodiandemo.beans.BaoCunBeanDao;
import com.xiaojun.yaodiandemo.beans.BenDiShenFenZhenDao;
import com.xiaojun.yaodiandemo.beans.TianJiaYaoDao;
import com.xiaojun.yaodiandemo.beans.UserInfoBenaDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig baoCunBeanDaoConfig;
    private final DaoConfig benDiShenFenZhenDaoConfig;
    private final DaoConfig tianJiaYaoDaoConfig;
    private final DaoConfig userInfoBenaDaoConfig;

    private final BaoCunBeanDao baoCunBeanDao;
    private final BenDiShenFenZhenDao benDiShenFenZhenDao;
    private final TianJiaYaoDao tianJiaYaoDao;
    private final UserInfoBenaDao userInfoBenaDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        baoCunBeanDaoConfig = daoConfigMap.get(BaoCunBeanDao.class).clone();
        baoCunBeanDaoConfig.initIdentityScope(type);

        benDiShenFenZhenDaoConfig = daoConfigMap.get(BenDiShenFenZhenDao.class).clone();
        benDiShenFenZhenDaoConfig.initIdentityScope(type);

        tianJiaYaoDaoConfig = daoConfigMap.get(TianJiaYaoDao.class).clone();
        tianJiaYaoDaoConfig.initIdentityScope(type);

        userInfoBenaDaoConfig = daoConfigMap.get(UserInfoBenaDao.class).clone();
        userInfoBenaDaoConfig.initIdentityScope(type);

        baoCunBeanDao = new BaoCunBeanDao(baoCunBeanDaoConfig, this);
        benDiShenFenZhenDao = new BenDiShenFenZhenDao(benDiShenFenZhenDaoConfig, this);
        tianJiaYaoDao = new TianJiaYaoDao(tianJiaYaoDaoConfig, this);
        userInfoBenaDao = new UserInfoBenaDao(userInfoBenaDaoConfig, this);

        registerDao(BaoCunBean.class, baoCunBeanDao);
        registerDao(BenDiShenFenZhen.class, benDiShenFenZhenDao);
        registerDao(TianJiaYao.class, tianJiaYaoDao);
        registerDao(UserInfoBena.class, userInfoBenaDao);
    }
    
    public void clear() {
        baoCunBeanDaoConfig.clearIdentityScope();
        benDiShenFenZhenDaoConfig.clearIdentityScope();
        tianJiaYaoDaoConfig.clearIdentityScope();
        userInfoBenaDaoConfig.clearIdentityScope();
    }

    public BaoCunBeanDao getBaoCunBeanDao() {
        return baoCunBeanDao;
    }

    public BenDiShenFenZhenDao getBenDiShenFenZhenDao() {
        return benDiShenFenZhenDao;
    }

    public TianJiaYaoDao getTianJiaYaoDao() {
        return tianJiaYaoDao;
    }

    public UserInfoBenaDao getUserInfoBenaDao() {
        return userInfoBenaDao;
    }

}
