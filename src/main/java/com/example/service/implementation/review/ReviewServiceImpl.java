package com.example.service.implementation.review;

import com.example.dto.PublishedReviewDTO;
import com.example.dto.TemporaryReviewDTO;
import com.example.entity.enums.ReviewStatus;
import com.example.entity.review.PublishedReview;
import com.example.entity.review.TemporaryReview;
import com.example.entity.user.Administrator;
import com.example.entity.user.User;
import com.example.exception.UserNotFoundException;
import com.example.mapper.PublishedReviewMapper;
import com.example.mapper.TemporaryReviewMapper;
import com.example.repository.PublishedReviewRepository;
import com.example.repository.TemporaryReviewRepository;
import com.example.service.interfaces.review.ReviewService;
import com.example.service.interfaces.user.AdministratorService;
import com.example.service.interfaces.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final TemporaryReviewRepository temporaryReviewRepository;
    private final PublishedReviewRepository publishedReviewRepository;
    private final TemporaryReviewMapper temporaryReviewMapper;
    private final PublishedReviewMapper publishedReviewMapper;
    private final AdministratorService administratorService;
    private final UserService userService;

    @Autowired
    public ReviewServiceImpl(TemporaryReviewRepository temporaryReviewRepository,
                             PublishedReviewRepository publishedReviewRepository,
                             TemporaryReviewMapper temporaryReviewMapper,
                             PublishedReviewMapper publishedReviewMapper,
                             AdministratorService administratorService,
                             UserService userService) {
        this.temporaryReviewRepository = temporaryReviewRepository;
        this.publishedReviewRepository = publishedReviewRepository;
        this.temporaryReviewMapper = temporaryReviewMapper;
        this.publishedReviewMapper = publishedReviewMapper;
        this.administratorService = administratorService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void saveNewTemporaryReview(TemporaryReviewDTO dto) throws UserNotFoundException {
        User author = userService.findUserByUsername(dto.getUserLogin());
        if(author == null)
            throw new UserNotFoundException("No such user");

        TemporaryReview review = temporaryReviewMapper.mapDTOToEntity(dto);
        Administrator inspector = administratorService.findAdminWithMinWork();

        review.setAuthor(author);
        review.setInspector(inspector);
        review.setReviewStatus(ReviewStatus.ON_REVISION);

        temporaryReviewRepository.save(review);
    }

    @Override
    @Transactional
    public void updateTemporaryReview(TemporaryReviewDTO dto) throws NullPointerException {
        TemporaryReview temporaryReview = temporaryReviewRepository.findTemporaryReviewById(dto.getReviewId());
        if(temporaryReview == null)
            throw new NullPointerException("No such review");
        temporaryReviewMapper.updateEntity(temporaryReview, dto);
        temporaryReviewRepository.save(temporaryReview);
    }

    @Override
    //todo make transactional
    //todo отобразить в бпмн что временая рецензия удаляется и добавляется новая опубликованная
    public void savePublishedReview(TemporaryReview review) {
        PublishedReview entity = publishedReviewMapper.mapTemporaryReviewToPublishedReview(review);
        temporaryReviewRepository.delete(review);
        publishedReviewRepository.save(entity);
    }

    @Override
    @Transactional
    public void updateTemporaryReviewStatus(TemporaryReview review) {
        temporaryReviewRepository.save(review);
    }

    @Override
    public TemporaryReview getTemporaryReviewById(Long id) {
        return temporaryReviewRepository.findTemporaryReviewById(id);
    }

    @Override
    public PublishedReview getPublishedReviewById(Long id) {
        return publishedReviewRepository.findPublishedReviewById(id);
    }

    @Override
    public List<TemporaryReviewDTO> getAllTemporaryReviews(Long adminId) {
        Administrator inspector = administratorService.findAdministratorById(adminId);
        List<TemporaryReview> temporaryReviews = temporaryReviewRepository.findAllByInspector(inspector);
        return temporaryReviewMapper.mapEntityListToDTOList(temporaryReviews);
    }

    @Override
    public List<PublishedReviewDTO> getAllPublishedReviews(Long userId) {
        User author = userService.findUserById(userId);
        List<PublishedReview> publishedReviews = publishedReviewRepository.findAllByAuthor(author);
        return publishedReviewMapper.mapEntityListToDTOList(publishedReviews);
    }
}
