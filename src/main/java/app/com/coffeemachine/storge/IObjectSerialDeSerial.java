package app.com.coffeemachine.storge;

public interface IObjectSerialDeSerial {

    public void serialize(Object o);
    public Object deSerialize(Class<?> clazz);
}
