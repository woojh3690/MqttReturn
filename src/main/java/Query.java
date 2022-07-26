public class Query {
    public final static String MQTT_TOPIC_COL = "mqtt_topic";

    public final static String MQTT_TOPIC_QUERY_FORM =
            "select CONCAT('/C', a.complex_code,'/C', a.complex_name,'/C', b.building_in_complex) as %s from smart_housing_complex_info a, " +
                "(" +
                    "(select complex_id,building_in_complex from smart_housing_individual_info where individual_id=" +
                        "(select individual_id from smart_housing_individual_user_permission where user_id=" +
                            "(SELECT user_id FROM smart_housing_user_view where source_key='%s')" +
                        ")" +
                    ")" +
                ") b " +
            "where a.complex_id=b.complex_id;";
}
