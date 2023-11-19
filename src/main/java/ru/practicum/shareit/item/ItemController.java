package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.BadDataRequest;
import ru.practicum.shareit.exception.NotFound;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collection;
import java.util.Map;


@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @PostMapping
    public ItemDto addItem(@RequestBody ItemDto item,
                           @RequestHeader("X-Sharer-User-Id") Integer userId) throws BadDataRequest, NotFound {
        return itemService.addItem(item, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestBody ItemDto item,
                              @PathVariable Integer itemId,
                              @RequestHeader("X-Sharer-User-Id") Integer userId) throws NotFound {
        return itemService.updateItem(item, itemId, userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@PathVariable Integer itemId,
                           @RequestHeader("X-Sharer-User-Id") Integer userId) throws NotFound {
        return itemService.getItem(itemId, userId);
    }

    @GetMapping
    public Collection<ItemDto> getUserItems(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemService.getUserItems(userId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> searchItems(@RequestParam(name = "text") String text,
                                           @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemService.searchItems(text, userId);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> notFound(final NotFound e) {
        return Map.of("errorMessage", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> badData(final BadDataRequest e) {
        return Map.of("errorMessage", e.getMessage());
    }
}
