import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int ultrasoundBusyUntil = 0;
        ArrayList<MedicalNotification> waiting = new ArrayList<>();

        while (true) {
            int currentHour = HospitalInformation.getHourCount();
            System.out.println("Hospital hour: " + currentHour);

            MedicalNotification note = HospitalInformation.getMedicalNotificationFromDoctor();
            String bay = note.getBay();
            String service = note.getService();

            if ("None".equals(service)) {
                System.out.println("No new request");
            } else {
                System.out.println("Requested: " + service + " for " + bay);

                if ("Ultrasound".equals(service)) {
                    waiting.add(note);
                }
            }

            if (!waiting.isEmpty() && currentHour >= ultrasoundBusyUntil) {
                MedicalNotification next = waiting.remove(0);
                HospitalInformation.requestPorter(next.getBay(), next.getService());
                ultrasoundBusyUntil = currentHour + 3;
            }

            Thread.sleep(3000);
        }
    }
}