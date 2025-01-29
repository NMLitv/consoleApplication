public class Сourier {

    private String courierName;
    private String lastname;
    private String competence;
    private double x;
    private double y;
    private double speed;
    private boolean allowed;
    private String address;

    public Сourier(String courierName, String lastname, String competence, double x,
                   double y, double speed, boolean allowed, String address){
        this.courierName = courierName;
        this.lastname = lastname;
        this.competence = competence;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.allowed = allowed;
        this.address = address;
    }

    public String getCourierName(){
        return this.courierName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCourierName(String courierName){
        this.courierName = courierName;
    }

    public String getLastname(String lastname){
        return this.lastname;
    }

    public void setLastname(String lastname){
        this.lastname = lastname;
    }

    public double getX(){
        return this.x;
    }

    public void setX(int x){
        this.x = x;
    }

    public double getY(){
        return this.y;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }
}
