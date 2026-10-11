package be.depairon.espaceclient.eglobal.utils.devextreme;

import be.depairon.espaceclient.eglobal.utils.STR;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class QueryBuilder {
    private final ObjectMapper mapper;

    private final static String LIKE = "contains";
    private final static String NOT_LIKE = "notcontains";
    private final static String START_WITH = "startswith";
    private final static String END_WITH = "endswith";
    private final static String EQUALS = "=";
    private final static String NOT_EQUALS = "<>";
    private final static String GREATER_EQUALS = ">=";
    private final static String GREATER = ">";
    private final static String LOWER_EQUALS = "<=";
    private final static String LOWER = "<";
    private final static String OR = "or";
    private final static String AND = "and";

    private List<?> conditionList;
    private List<DESort> sortList;
    private List<DEHeaderFilter> headerFilterList;

    public QueryBuilder(ObjectMapper mapper) {
        this.mapper = mapper;
    }


    public void initializeConditionList(String conditionFilter) {
        try {
            this.conditionList = mapper.readValue(conditionFilter, List.class);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }


    public void initializeConditionList(String filter, Map<String, String> generateConvertTable) {
        try {
            this.conditionList = mapper.readValue(filter, List.class);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void initializeSortList(String sorFilter) {
        try {
            this.sortList = Arrays.asList(mapper.readValue(sorFilter, DESort[].class));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void initializeSortList(String sort, Map<String, String> convertTable) {
        try {
            List<DESort> deSortList = Arrays.asList(mapper.readValue(sort, DESort[].class));
            this.sortList = new ArrayList<>();
            deSortList.forEach(deSort -> this.sortList.add(new DESort(convertTable.get(deSort.getSelector()), deSort.getDesc())));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void initializeHeaderFilter(String requestFilter) {
        try {
            this.headerFilterList = Arrays.asList(mapper.readValue(requestFilter, DEHeaderFilter[].class));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }


    public String buildOrderByClause() {
        if (CollectionUtils.isNotEmpty(this.sortList)) {
            StringBuilder orderByClause = new StringBuilder(" order by ");
            for (int i = 0; i < this.sortList.size() - 1; i++) {
                orderByClause.append(this.sortList.get(i).getSelector()).append(" ").append(this.sortList.get(i).getDesc() ? " desc " : " ").append(", ");
            }
            orderByClause.append(this.sortList.get(this.sortList.size() - 1).getSelector()).append(" ").append(this.sortList.get(this.sortList.size() - 1).getDesc() ? " desc " : " ");
            return orderByClause.toString();
        }
        return "";

    }

    public String buildWhereClause() {
        if (CollectionUtils.isNotEmpty(this.conditionList)) {
            StringBuilder whereClause = new StringBuilder(" where ");
            buildDetail(whereClause, this.conditionList, false);
            return whereClause.toString();
        }
        return "";
    }

    public String buildHeaderFilterSelectClause() {
        if (CollectionUtils.isNotEmpty(this.headerFilterList)) {
            StringBuilder headerFilterValue = new StringBuilder();
            for (int i = 0; i < this.headerFilterList.size() - 1; i++) {
                headerFilterValue.append(this.headerFilterList.get(i).getSelector()).append(", ");
            }
            headerFilterValue.append(this.headerFilterList.get(this.headerFilterList.size() - 1).getSelector()).append(" ");
            if (this.headerFilterList != null && StringUtils.isNotBlank(headerFilterValue.toString())) {
                return "select distinct " + headerFilterValue;
            }
        }
        return "";
    }

    public String buildHeaderFilterCountClause() {
        if (CollectionUtils.isNotEmpty(this.headerFilterList)) {
            String headerFilterValue = this.headerFilterList.get(0).getSelector();
            if (this.headerFilterList != null && StringUtils.isNotBlank(headerFilterValue)) {
                return "select count(distinct " + headerFilterValue + ")";
            }
        }
        return "";
    }

    private void buildDetail(StringBuilder builder, Object item, boolean negateOperator) {
        try {
            if (item instanceof List<?>) {
                List<?> itemList = (List<?>) item;
                builder.append("(");
                if (itemList.get(0) instanceof List<?>) {
                    for (int i = 0; i < itemList.size(); i++) {
                        buildDetail(builder, itemList.get(i), negateOperator);
                    }
                } else {
                    Object key = itemList.get(0);
                    if ("!".equals(key)) {
                        List<?> not = (List<?>) itemList.get(1);
                        buildDetail(builder, not, true);
                    } else {
                        Object operator = itemList.get(1);
                        Object value = itemList.get(2);

                        if (StringUtils.containsIgnoreCase(key.toString(), "date")) {
                            value = STR.Complete(value.toString().replace("/", "").replace("-", ""), 8);
                        }
                        builder.append(key)
                                .append(" ")
                                .append(negateOperator
                                        ? parseFilterOperator(NOT_EQUALS, value)
                                        : parseFilterOperator(operator.toString(), value));
                    }
                }
                builder.append(")");
            } else {
                if (negateOperator) {
                    if (OR.equals(item.toString())) {
                        builder.append(" ").append(AND).append(" ");
                    } else {
                        builder.append(" ").append(OR).append(" ");
                    }
                } else {
                    builder.append(" ").append(item.toString()).append(" ");
                }
            }
        } catch (Exception e) {
            // Clear builder to avoid error with where condition
            builder.setLength(0);
            System.out.println(e.getLocalizedMessage());
        }
    }

    private String parseFilterOperator(String filterOperator, Object filterValue) {
        String quote = (filterValue instanceof String) ? "'" : "";
        StringBuilder builder = new StringBuilder();
        if (filterOperator.equalsIgnoreCase(NOT_LIKE)) {
            return builder.append(" not like ").append(quote).append("%").append(filterValue).append("%' ").toString();
        } else if (filterOperator.equalsIgnoreCase(START_WITH)) {
            return builder.append(" like '").append(filterValue).append("%' ").toString();
        } else if (filterOperator.equalsIgnoreCase(END_WITH)) {
            return builder.append(" like '%").append(filterValue).append("' ").toString();
        } else if (filterOperator.equalsIgnoreCase(EQUALS)) {
            return builder.append(" = '").append(filterValue).append("' ").toString();
        } else if (filterOperator.equalsIgnoreCase(NOT_EQUALS)) {
            return builder.append(" <> '").append(filterValue).append("' ").toString();
        } else if (filterOperator.equalsIgnoreCase(GREATER_EQUALS)) {
            return builder.append(" >= ").append(filterValue).append(" ").toString();
        } else if (filterOperator.equalsIgnoreCase(GREATER)) {
            return builder.append(" > ").append(filterValue).append(" ").toString();
        } else if (filterOperator.equalsIgnoreCase(LOWER_EQUALS)) {
            return builder.append(" <= ").append(filterValue).append(" ").toString();
        } else if (filterOperator.equalsIgnoreCase(LOWER)) {
            return builder.append(" < ").append(filterValue).append(" ").toString();
        } else {
            return builder.append(" like '%").append(filterValue).append("%' ").toString();
        }
    }
}
