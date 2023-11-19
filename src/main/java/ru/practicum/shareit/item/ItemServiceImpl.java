package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.BadDataRequest;
import ru.practicum.shareit.exception.NotFound;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(UserRepository userRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemDto addItem(ItemDto item, Integer userId) throws NotFound, BadDataRequest {
        User itemOwner = userRepository.get(userId);
        Item addedItem = itemRepository.add(ItemMapper.toItem(item, itemOwner));
        return ItemMapper.toItemDto(addedItem);
    }

    @Override
    public ItemDto updateItem(ItemDto item, Integer itemId, Integer userId) throws NotFound {
        User itemOwner = userRepository.get(userId);
        Item itemForUpdate = ItemMapper.toItem(item, itemOwner);
        return ItemMapper.toItemDto(itemRepository.update(itemForUpdate, itemId));
    }

    @Override
    public ItemDto getItem(Integer itemId, Integer userId) throws NotFound {
        return ItemMapper.toItemDto(itemRepository.get(itemId));
    }

    @Override
    public Collection<ItemDto> getUserItems(Integer userId) {
        Collection<Item> result = itemRepository.getUserItems(userId);
        return result.stream()
                .map(i -> ItemMapper.toItemDto(i))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> searchItems(String searchingText, Integer userId) {
        Collection<Item> result = itemRepository.searchItems(searchingText);
        return result.stream()
                .map(i -> ItemMapper.toItemDto(i))
                .collect(Collectors.toList());
    }
}
