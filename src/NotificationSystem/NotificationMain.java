package NotificationSystem;
import NotificationSystem.model.*;
import NotificationSystem.decorator.*;
import NotificationSystem.service.NotificationService;
public class NotificationMain {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║   🔔 NOTIFICATION SYSTEM DEMO     ║");
        System.out.println("╚════════════════════════════════════╝\n");

        System.out.println("═══ SCENARIO 1: SMS only ═══\n");
        Notifier smsOnly = new SMSDecorator(new BaseNotifier());
        new NotificationService(smsOnly).notify("Alice", "Your order has shipped!");

        System.out.println("═══ SCENARIO 2: Email + Push ═══\n");
        Notifier emailPush = new PushDecorator(new EmailDecorator(new BaseNotifier()));
        new NotificationService(emailPush).notify("Bob", "Payment received: $49.99");

        System.out.println("═══ SCENARIO 3: All channels (SMS + Email + Push + Slack) ═══\n");
        Notifier allChannels = new SlackDecorator(new PushDecorator(new EmailDecorator(new SMSDecorator(new BaseNotifier()))));
        new NotificationService(allChannels).notify("Charlie", "🚨 Server alert: CPU > 90%");

        System.out.println("═══ SCENARIO 4: Just base (logging only) ═══\n");
        new NotificationService(new BaseNotifier()).notify("Diana", "Internal audit log");

        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║   ✅ ALL SCENARIOS COMPLETE         ║");
        System.out.println("╚════════════════════════════════════╝");
    }
}
