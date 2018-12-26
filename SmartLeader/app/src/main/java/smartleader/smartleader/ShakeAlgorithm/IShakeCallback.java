package smartleader.smartleader.ShakeAlgorithm;
/*
 * IShaekCallback
 * ShakeAlgorithm 에서 사용할 Callback 메소드를 가진 인터페이스
 * function : 리스너 등록, 리스너 제거, 리스너 여부확인
 * 상호작용 : DoorOpenService
 */
public interface IShakeCallback {
    void registerListener();
    void removeListener();
    boolean isListenerSet();
}
