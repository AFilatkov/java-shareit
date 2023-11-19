package ru.practicum.shareit.item;

import ru.practicum.shareit.exception.BadDataRequest;
import ru.practicum.shareit.exception.NotFound;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemRepository {
    Item add(Item item) throws BadDataRequest;

    Item update(Item item, Integer itemId) throws NotFound;

    Item get(Integer itemId) throws NotFound;

    Collection<Item> getUserItems(Integer userId);

    Collection<Item> searchItems(String searchingText);
}
