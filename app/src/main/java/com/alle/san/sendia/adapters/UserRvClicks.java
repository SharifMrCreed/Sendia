package com.alle.san.sendia.adapters;

import com.alle.san.sendia.models.Message;
import com.alle.san.sendia.models.User;

public interface UserRvClicks {
    void whenUserIsClicked(User user);
    void whenMessageIsClicked(Message message);
}
