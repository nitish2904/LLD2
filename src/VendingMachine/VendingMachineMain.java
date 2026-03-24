package VendingMachine;
import VendingMachine.model.Product;
import VendingMachine.state.VendingContext;
public class VendingMachineMain {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║   🥤 VENDING MACHINE DEMO         ║");
        System.out.println("╚════════════════════════════════════╝\n");
        VendingContext vm = new VendingContext();
        vm.addProduct(new Product("A1", "Coca Cola", 1.50, 5));
        vm.addProduct(new Product("A2", "Pepsi", 1.50, 3));
        vm.addProduct(new Product("B1", "Snickers", 2.00, 2));
        vm.addProduct(new Product("B2", "KitKat", 1.75, 0));
        vm.printInventory();

        System.out.println("\n═══ SCENARIO 1: Normal Purchase ═══\n");
        vm.insertCoin(1.00); vm.insertCoin(0.50); vm.selectProduct("A1");

        System.out.println("\n═══ SCENARIO 2: Purchase with Change ═══\n");
        vm.insertCoin(2.00); vm.insertCoin(1.00); vm.selectProduct("B1");

        System.out.println("\n═══ SCENARIO 3: Insufficient Funds ═══\n");
        vm.insertCoin(0.50); vm.selectProduct("A2"); vm.insertCoin(1.00); vm.selectProduct("A2");

        System.out.println("\n═══ SCENARIO 4: Out of Stock ═══\n");
        vm.insertCoin(2.00); vm.selectProduct("B2"); vm.cancel();

        System.out.println("\n═══ SCENARIO 5: Cancel Transaction ═══\n");
        vm.insertCoin(5.00); vm.cancel();

        System.out.println("\n═══ SCENARIO 6: No Money Inserted ═══\n");
        vm.selectProduct("A1"); vm.cancel();

        vm.printInventory();
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║   ✅ ALL SCENARIOS COMPLETE         ║");
        System.out.println("╚════════════════════════════════════╝");
    }
}
