package smartleader.smartleader.ServerCallback;

import javax.security.auth.callback.Callback;

public interface IServerCallback extends Callback {
    void ConnectionError();
}
