package HotelManagement.strategy;
import HotelManagement.model.*;
import java.util.List;
import java.util.Optional;
public interface RoomSelectionStrategy {
    Optional<Room> selectRoom(List<Room> rooms, RoomType type);
    String getName();
}
