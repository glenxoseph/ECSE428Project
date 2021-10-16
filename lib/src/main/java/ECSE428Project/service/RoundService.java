package ECSE428Project.service;

import ECSE428Project.dao.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoundService {

    @Autowired
    private RoundRepository roundRepository;
}
