######source
{
    "name": "zz-001",
    "config": {
        "connector.class": "io.debezium.connector.mysql.MySqlConnector",
        "tasks.max": "1",
        "database.hostname": "127.0.0.1",
        "database.port": "3306",
        "database.user": "root",
        "database.password": "root",
        "database.server.id": "9981",
        "database.server.name": "test",
        "database.whitelist": "test",
        "schema.include.list":"test.test_copy1",
        "table.include.list": "test.test_copy1",
        "database.history.kafka.bootstrap.servers": "localhost:9092",
        "bootstrap.servers":"localhost:9092",
        "database.history.kafka.topic": "schema-changes.inventory",
        "transforms": "route",
        "transforms.route.type": "org.apache.kafka.connect.transforms.RegexRouter",
        "transforms.route.regex": "([^.]+)\\.([^.]+)\\.([^.]+)",
        "transforms.route.replacement": "$3",
        "value.converter": "org.apache.kafka.connect.json.JsonConverter",
"value.converter.schemas.enable": "false"
    }
}
#####sink
{
    "name": "ttt111",
    "config": {
        "connector.class": "io.confluent.connect.jdbc.JdbcSinkConnector",
        "tasks.max": "1",
        "topics": "test_copy1",
        "connection.url": "jdbc:mysql://127.0.0.1:3306/sample",
        "connection.password":"root",
        "connection.user":"root",
        "key.ignore": "false",
        "type.name": "customer",
        "behavior.on.null.values": "delete",
                "value.converter": "org.apache.kafka.connect.json.JsonConverter",
"value.converter.schemas.enable": "false",
"delete.enabled":"true",
"pk.mode":"record_key",
"transforms.key.field": "id",
"auto.create":"true",
"auto.evolve":"true"
    }
}
