package com.bloger.web.blog.services;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Data
@Service
public class SubscribeService {

    private List<String> subscribeList = new ArrayList<>();

    public void addSubscribeObj(String userLogin) {
        if (!subscribeList.contains(userLogin)) {
            subscribeList.add(userLogin);
        }

        System.out.println(getAllSubscribe());
    }

    public List<String> getAllSubscribe() {
        return subscribeList;
    }
}