package HotelManagement.strategy;
import HotelManagement.model.*;
import java.util.*;
public class CheapestRoomStrategy implements RoomSelectionStrategy {
    public Optional<Room> selectRoom(List<Room> rooms, RoomType type) {
        return rooms.stream().filter(r -> r.getType() == type && r.isAvailable()).min(Comparator.comparingDouble(Room::getPricePerNight));
    }
    public String getName() { return "Cheapest"; }
}
