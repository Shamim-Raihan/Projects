class BusModel {
  int? id;
  String from;
  String to;
  String busName;
  String date;
  String time;
  String fare;
  String busType;
  String totalSeat;
  String availableSeat;
  String busUniqueNo;
  String rating;
  int ratingCount;

  BusModel(
      {this.id,
      required this.from,
      required this.to,
      required this.busName,
      required this.date,
      required this.time,
      required this.fare,
      required this.busType,
      required this.totalSeat,
      required this.availableSeat,
      required this.busUniqueNo,
      required this.rating,
      required this.ratingCount});

  Map<String, dynamic> toMap() {
    var map = <String, dynamic>{
      busTableNameColFrom: from,
      busTableNameColTo: to,
      busTableNameColName: busName,
      busTableNameColDate: date,
      busTableNameColTime: time,
      busTableNameColFare: fare,
      busTableNameColBusType: busType,
      busTableNameColTotalSeat: totalSeat,
      busTableNameColAvailableSeat: availableSeat,
      busTableNameColBusUniqueId: busUniqueNo,
      busTableNameColBusRating: rating,
      busTableNameColBusRatingCount: ratingCount,
    };
    if (id != null) {
      map[busTableNameColId] = id;
    }
    return map;
  }

  factory BusModel.fromMap(Map<String, dynamic> map) => BusModel(
        id: map[busTableNameColId],
        from: map[busTableNameColFrom],
        to: map[busTableNameColTo],
        busName: map[busTableNameColName],
        date: map[busTableNameColDate],
        time: map[busTableNameColTime],
        fare: map[busTableNameColFare],
        busType: map[busTableNameColBusType],
        totalSeat: map[busTableNameColTotalSeat],
        availableSeat: map[busTableNameColAvailableSeat],
        busUniqueNo: map[busTableNameColBusUniqueId],
        rating: map[busTableNameColBusRating],
        ratingCount: map[busTableNameColBusRatingCount],
      );
}

const String busTableName = 'bus_table';

const String busTableNameColId = 'id';
const String busTableNameColFrom = 'bus_from';
const String busTableNameColTo = 'bus_to';
const String busTableNameColName = 'bus_name';
const String busTableNameColDate = 'date';
const String busTableNameColTime = 'time';
const String busTableNameColFare = 'fare';
const String busTableNameColBusType = 'bus_type';
const String busTableNameColTotalSeat = 'total_seat';
const String busTableNameColAvailableSeat = 'available_seat';
const String busTableNameColBusUniqueId = 'bus_unique_no';
const String busTableNameColBusRating = 'bus_rating';
const String busTableNameColBusRatingCount = 'bus_rating_count';
