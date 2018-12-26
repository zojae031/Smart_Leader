package smartleader.smartleader.ShakeAlgorithm;

/*
 * LocationHolder
 * Shake_Algorithm 의 좌표정보를 저장/관리 하는 클래스
 * function : shake 알고리즘의 계산을 수행
 * @Author : 조재영
 */
public class LocationHolder {
    private float lastX, lastY, lastZ;
    private float x, y, z;

    public LocationHolder() {
        lastZ = 0;
        lastY = 0;
        lastX = 0;
        x = 0;
        y = 0;
        z = 0;
    }

    public void setCurrentLocation(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setLastLocation(float x, float y, float z) {
        lastX = x;
        lastY = y;
        lastZ = z;
    }

    public float calculateSubtraction() {
        //값계산이 장애인처럼되서 좌측으로만 계산
//        float result = (x + y + z) - (lastX + lastY + lastZ);
        float result = x-lastX;
        return result;
    }
}