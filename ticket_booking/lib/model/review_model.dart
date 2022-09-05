class ReviewModel {
  int? id;
  String review;
  int userId;
  int busId;

  ReviewModel(
      {this.id,
      required this.review,
      required this.userId,
      required this.busId});



  Map<String, dynamic> toMap() {
    var map = <String, dynamic>{
      reviewTableColReview: review,
      reviewTableColUserId: userId,
      reviewTableColBusId: busId,
    };
    if (userId != null) {
      map[reviewTableColReviewId] = id;
    }
    return map;
  }

  factory ReviewModel.fromMap(Map<String, dynamic> map) => ReviewModel(
      id: map[reviewTableColReviewId],
      review: map[reviewTableColReview],
      userId: map[reviewTableColUserId],
      busId: map[reviewTableColBusId]);
}

const String reviewTableName = 'review_table';

const String reviewTableColReviewId = 'review_id';
const String reviewTableColReview = 'review';
const String reviewTableColUserId = 'user_id';
const String reviewTableColBusId = 'bus_id';
