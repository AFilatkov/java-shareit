package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.BadDataRequest;
import ru.practicum.shareit.exception.NotFound;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class InMemoryItemRepository implements ItemRepository {
    private final Map<Integer, Item> items = new HashMap<>();
    private Integer counter = 0;

    @Override
    public Item add(Item item) throws IllegalArgumentException, BadDataRequest {
        if (item.getAvailable() == null) {
            throw new BadDataRequest("При добавлении вещи не указана доступность");
        }
        if (item.getName() == null) {
            throw new BadDataRequest("При добавлении вещи не указано имя");
        }
        if (item.getName().isEmpty() || item.getName().isBlank()) {
            throw new BadDataRequest("Имя не может быть пустым");
        }
        if (item.getDescription() == null) {
            throw new BadDataRequest("При добавлении вещи не указано описание");
        }
        if (item.getDescription().isEmpty() || item.getDescription().isBlank()) {
            throw new BadDataRequest("Описание не может быть пустым");
        }
        items.put(++counter, item);
        item.setId(counter);
        return item;
    }

    @Override
    public Item update(Item item, Integer itemId) throws NotFound {
        if (items.containsKey(itemId)) {
            Item toUpdate = items.get(itemId);
            if (toUpdate.getOwner().getId() == item.getOwner().getId()) {
                if (item.getName() != null) {
                    toUpdate.setName(item.getName());
                }
                if (item.getDescription() != null) {
                    toUpdate.setDescription(item.getDescription());
                }
                if (item.getAvailable() != null) {
                    toUpdate.setAvailable(item.getAvailable());
                }
                return toUpdate;
            } else {
                throw new NotFound("Только владелец вещи может обновить её");
            }
        } else {
            throw new NotFound("Указан неправильний идентификатор вещи");
        }
    }

    @Override
    public Item get(Integer itemId) throws NotFound {
        if (items.containsKey(itemId)) {
            return items.get(itemId);
        } else {
            throw new NotFound("Указан неправильний идентификатор вещи");
        }
    }

    @Override
    public Collection<Item> getUserItems(Integer userId) {
        return items.values().stream()
                .filter(item -> item.getOwner().getId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Item> searchItems(String searchingText) {
        if (searchingText == null) {
            return Collections.emptyList();
        }
        if (searchingText.isEmpty()) {
            return Collections.emptyList();
        }
        return items.values().stream()
                .filter(i -> (i.getDescription().toLowerCase().contains(searchingText.toLowerCase()) ||
                        i.getName().toLowerCase().contains(searchingText.toLowerCase())) &&
                        i.getAvailable())
                .collect(Collectors.toList());
    }
}
