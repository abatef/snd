package com.snd.snxdbackend.services;

import com.snd.snxdbackend.dtos.Notification;
import com.snd.snxdbackend.enums.NotificationType;
import com.snd.snxdbackend.models.Product;
import com.snd.snxdbackend.models.User;
import com.snd.snxdbackend.models.wishlist.WishlistItem;
import com.snd.snxdbackend.repositories.UserRepository;
import com.snd.snxdbackend.repositories.WishlistItemRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    private final WishlistItemRepository wishlistItemRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public NotificationService(WishlistItemRepository wishlistItemRepository, RedisTemplate<String, Object> redisTemplate) {
        this.wishlistItemRepository = wishlistItemRepository;
        this.redisTemplate = redisTemplate;
    }

    @Async
    public void notifyUsersOfWishlistChange(Integer productId, NotificationType notificationType, boolean down, double value) {
        String msg;
        Notification notification =  new Notification();
        notification.setType(notificationType);
        List<WishlistItem> wishlistItems = wishlistItemRepository.findWishlistItemsByProductId(productId);
        for (WishlistItem wishlistItem : wishlistItems) {
            User user = wishlistItem.getWishlist().getUser();
            msg = "Product: " + wishlistItem.getProduct().getName();
            if (notificationType == NotificationType.WISHLIST_PRICE_UPDATE) {
                if (down) {
                    msg += "Price Went Down ";
                } else {
                    msg += "Price Went Up ";
                }
                msg += "From " + value + " to " + wishlistItem.getProduct().getPrice();
            } else {
                msg += "Stock Updated";
            }
            notification.setMessage(msg);
            redisTemplate.opsForList().rightPush("user:" + user.getId() + ":notification", notification);
        }
    }

    public List<Notification> getUserNotifications(Integer userId) {
        List<Notification> notifications = new ArrayList<>();
        Long l = redisTemplate.opsForList().size("user:" + userId + ":notification");
        while (l != null && l >= 0) {
            notifications.add((Notification) redisTemplate.opsForList().index("user:" + userId + ":notification", l));
            l--;
        }
        return notifications;
    }
}
