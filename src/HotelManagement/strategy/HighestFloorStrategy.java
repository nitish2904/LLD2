package HotelManagement.strategy;
import HotelManagement.model.*;
import java.util.*;
public class HighestFloorStrategy implements RoomSelectionStrategy {
    public Optional<Room> selectRoom(List<Room> rooms, RoomType type) {
        return rooms.stream().filter(r -> r.getType() == type && r.isAvailable()).max(Comparator.comparingInt(Room::getFloor));
    }
    public String getName() { return "HighestFloor"; }
}
