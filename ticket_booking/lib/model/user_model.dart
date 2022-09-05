class UserModel {
  int? userId;
  String name;
  String email;
  String phone;
  String nid;
  String bookedSeat;
  String bookedSeatIndex;
  int busId;

  UserModel(
      {this.userId,
      required this.name,
      required this.email,
      required this.phone,
      required this.nid,
      required this.bookedSeat,
      required this.bookedSeatIndex,
      required this.busId});


  @override
  String toString() {
    return 'UserModel{userId: $userId, name: $name, email: $email, phone: $phone, nid: $nid, bookedSeat: $bookedSeat, bookedSeatIndex: $bookedSeatIndex, busId: $busId}';
  }

  Map<String, dynamic> toMap() {
    var map = <String, dynamic>{
      userTableColName: name,
      userTableColEmail: email,
      userTableColPhone: phone,
      userTableColNid: nid,
      userTableColBookedSeat: bookedSeat,
      userTableColBookedSeatIndex: bookedSeatIndex,
      userTableColBusId: busId,
    };
    if (userId != null) {
      map[userTableColUserId] = userId;
    }
    return map;
  }

  factory UserModel.fromMap(Map<String, dynamic> map) => UserModel(
      userId: map[userTableColUserId],
      name: map[userTableColName],
      email: map[userTableColEmail],
      phone: map[userTableColPhone],
      nid: map[userTableColNid],
      bookedSeat: map[userTableColBookedSeat],
      bookedSeatIndex: map[userTableColBookedSeatIndex],
      busId: map[userTableColBusId]);
}

const String userTableName = 'user_table';

const String userTableColUserId = 'user_id';
const String userTableColName = 'name';
const String userTableColEmail = 'email';
const String userTableColPhone = 'phone';
const String userTableColNid = 'nid';
const String userTableColBookedSeat = 'booked_seat';
const String userTableColBookedSeatIndex = 'booked_seat_index';
const String userTableColBusId = 'bus_id';
