import 'package:flutter/material.dart';
import 'package:ticket_booking/db/database_helper.dart';
import 'package:ticket_booking/model/review_model.dart';

class ReviewInfoProvider extends ChangeNotifier{

  List<ReviewModel> reviewList = [];

  Future<bool> addNewReview(ReviewModel reviewModel) async {
    final rowId = await DBHelper.insertReview(reviewModel);
    if(rowId > 0){
      reviewList.add(reviewModel);
      notifyListeners();
      return true;
    }
    else {
      return false;
    }
  }

  getAllReview(){
    DBHelper.getAllReview().then((value) {
      reviewList = value;
      notifyListeners();
    });
  }

  deleteReviewByBusId(int busId) async {
    final rowId = await DBHelper.deleteReviewbyBusId(busId);
    if(rowId > 0) {
      reviewList.removeWhere((element) => element.busId == busId);
      notifyListeners();
    }
  }

}