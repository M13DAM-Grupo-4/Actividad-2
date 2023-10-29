package m13dam.grupo4.actividad2.Types;

public class CurrentSession {
    private static int UserID;

    public static int getUserID() {
        return UserID;
    }

    public static void setUserID(int userID) {
        UserID = userID;
    }
}
