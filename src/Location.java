import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Location {
    private double latitude; // широта
    private double longitude; // долгота

    public Location(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // расчёт приблизительного расстояния в км
    public double distanceTo(Location other) {
        double latDiff = Math.abs(this.latitude - other.latitude);
        double lonDiff = Math.abs(this.longitude - other.longitude);
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff) * 111; // Приблизительное расстояние в км
    }

    // расчёт потраченного времени на путь между адресами
    public double TravelTime(double way, double speed) {
        if (speed <= 0) {
            throw new IllegalArgumentException("Скорость должна быть больше 0");
        }
        double time = way / speed;
        // Создаем DecimalFormat с локалью, где точка используется как разделитель
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat dF = new DecimalFormat("#.##", symbols);
        return Double.parseDouble(dF.format(time)); // Преобразуем отформатированное значение обратно в double
    }

    @Override
    public String toString() {
        return "Location{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
        }
}
