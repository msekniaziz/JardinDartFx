package tn.jardindart.controllers.user;

public class SessionManager {
    private static SessionManager instance;
    private String userId;

    private int userfront ;

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserFront(int userfront) {
        this.userfront = userfront;
    }
    public int getUserFront() {
       return userfront;
    }


    public String getUserId()
    {
        return userId;
    }
    public void cleanUserSessionAdmin() {
      userId= " " ;
         }
    public void cleanUserSessionFront() {
        userfront= 0;
    }

}
