/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer;

import com.vcl.analyzer.ui.AnalyzerConfig;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kyihein
 */
public class VisitCondition {

    public static final int EQ = 0;
    public static final int GT = 1;
    public static final int LT = 2;
    public static final int GTE = 3;
    public static final int LTE = 4;
    public static final int IN = 5;
    private int condition;
    private int value;
    private List<Integer> valueList;

    public VisitCondition(String condition, String value) {
        switch(condition) {
            case AnalyzerConfig.ITEM_EQ:
                this.condition = EQ;
                this.value = Integer.parseInt(value);
                break;
            case AnalyzerConfig.ITEM_GT:
                this.condition = GT;
                this.value = Integer.parseInt(value);
                break;
            case AnalyzerConfig.ITEM_GTE:
                this.condition = GTE;
                this.value = Integer.parseInt(value);
                break;
            case AnalyzerConfig.ITEM_LT:
                this.condition = LT;
                this.value = Integer.parseInt(value);
                break;
            case AnalyzerConfig.ITEM_LTE:
                this.condition = LTE;
                this.value = Integer.parseInt(value);
                break;
            case AnalyzerConfig.ITEM_IN:
                this.condition = IN;
                this.valueList = parseIntList(value);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
    
    private List<Integer> parseIntList(String input) {
        List<Integer> valueList = new ArrayList<>();
        
        if(input.indexOf(",") != -1) {
            String[] tmpList = input.split(",");
            for(String s : tmpList) {
                valueList.add(Integer.parseInt(s));
            }
        } else if(input.indexOf(" ") != -1) {
            String[] tmpList = input.split(" ");
            for(String s : tmpList) {
                valueList.add(Integer.parseInt(s));
            }
        } else {
            valueList.add(Integer.parseInt(input));
        }
        
        return valueList;
    }
    
    public VisitCondition(int condition, int value) {
        if (condition == IN) {
            throw new IllegalArgumentException();
        }

        this.condition = condition;
        this.value = value;
    }

    public VisitCondition(int condition, List<Integer> valueList) {
        if (condition != IN) {
            throw new IllegalArgumentException();
        }

        this.condition = condition;
        this.valueList = valueList;
    }

    public String getVisitCondition() {
        StringBuilder sb = new StringBuilder();
        
        if (condition == IN) {
            sb.append("in(");
            for (Integer i : valueList) {
                sb.append(i);
                sb.append(",");
            }
            sb.setLength(sb.length() - 1);
            sb.append(")");
        } else {
            switch (condition) {
                case EQ:
                    sb.append("= ");
                    break;
                case GT:
                    sb.append("> ");
                    break;
                case LT:
                    sb.append("< ");
                    break;
                case GTE:
                    sb.append(">= ");
                    break;
                case LTE:
                    sb.append("<= ");
                    break;
            }
            sb.append(value);
        }
        return sb.toString();
    }
}
