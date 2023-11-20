package ru.practicum.shareit.item;

import ru.practicum.shareit.exception.BadDataRequest;
import ru.practicum.shareit.exception.NotFound;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collection;

public interface ItemService {
    ItemDto addItem(ItemDto item, Integer userId) throws IllegalArgumentException, NotFound, BadDataRequest;

    ItemDto updateItem(ItemDto item, Integer itemId, Integer userId) throws NotFound;

    ItemDto getItem(Integer itemId, Integer userId) throws NotFound;

    Collection<ItemDto> getUserItems(Integer userId);

    Collection<ItemDto> searchItems(String searchingText, Integer userId);
}
