package com.dayaeyak.exhibition.common.config;

import com.querydsl.jpa.DefaultQueryHandler;
import com.querydsl.jpa.Hibernate5Templates;
import com.querydsl.jpa.QueryHandler;

public class ExtendedHibernate5Templates extends Hibernate5Templates {

    @Override
    public QueryHandler getQueryHandler() {
        return DefaultQueryHandler.DEFAULT;
    }
}
