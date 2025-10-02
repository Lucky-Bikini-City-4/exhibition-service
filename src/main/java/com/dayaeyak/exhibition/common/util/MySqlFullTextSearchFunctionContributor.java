package com.dayaeyak.exhibition.common.util;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.type.BasicType;
import org.hibernate.type.StandardBasicTypes;

public class MySqlFullTextSearchFunctionContributor implements FunctionContributor {

    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        BasicType<Boolean> resultType = functionContributions.getTypeConfiguration()
                .getBasicTypeRegistry()
                .resolve(StandardBasicTypes.BOOLEAN);

        functionContributions.getFunctionRegistry()
                .registerPattern("match_against", "MATCH(?1) AGAINST(?2)", resultType);
    }
}
