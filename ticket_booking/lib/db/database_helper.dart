import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';
import 'package:ticket_booking/model/add_new_bus_model.dart';
import 'package:ticket_booking/model/admin_model.dart';
import 'package:ticket_booking/model/review_model.dart';
import 'package:ticket_booking/model/seat_model.dart';
import 'package:ticket_booking/model/user_model.dart';

class DBHelper {
  static const String _createAdminTable = '''create table $adminTableName(
  $adminTableNameColId integer primary key autoincrement,
  $adminTableNameColEmail text,
  $adminTableNameColpassword text)''';

  static const String _createBusTable = '''create table $busTableName(
  $busTableNameColId integer primary key autoincrement,
  $busTableNameColFrom text,
  $busTableNameColTo text,
  $busTableNameColName text,
  $busTableNameColDate text,
  $busTableNameColTime text,
  $busTableNameColFare text,
  $busTableNameColBusType text,
  $busTableNameColTotalSeat text,
  $busTableNameColAvailableSeat text,
  $busTableNameColBusUniqueId text,
  $busTableNameColBusRating text,
  $busTableNameColBusRatingCount integer)''';

  static const String _createSeatTable = '''create table $seatTableName(
  $seatTableColSeatId integer primary key autoincrement,
  $seatTableColSeatNo text,
  $seatTableColSeatIndex text,
  $seatTableColBusId integer)''';

  static const String _createUserTable = '''create table $userTableName(
  $userTableColUserId integer primary key autoincrement,
  $userTableColName text,
  $userTableColEmail text,
  $userTableColPhone text,
  $userTableColNid text,
  $userTableColBookedSeat text,
  $userTableColBookedSeatIndex text,
  $userTableColBusId integer)''';


  static const String _createReviewTable = '''create table $reviewTableName(
  $reviewTableColReviewId integer primary key autoincrement,
  $reviewTableColReview text,
  $reviewTableColUserId integer,
  $reviewTableColBusId integer)''';



  static Future<Database> open() async {
    final root = await getDatabasesPath();
    final dbPath = join(root, 'ticketBooking.db');
    return openDatabase(dbPath, version: 1, onCreate: (db, version) async {
      await db.execute(_createAdminTable);
      await db.execute(_createBusTable);
      await db.execute(_createSeatTable);
      await db.execute(_createUserTable);
      await db.execute(_createReviewTable);
    });
  }

  static Future<int> insertReview(ReviewModel reviewModel) async {
    final db = await open();
    return db.insert(reviewTableName, reviewModel.toMap());
  }

  static Future<int> insertAdmin(AdminModel adminModel) async {
    final db = await open();
    return db.insert(adminTableName, adminModel.toMap());
  }

  static getAdminInfo(int id) async {
    final db = await open();
    final amdinInfo = await db.query(adminTableName,
        where: '$adminTableNameColId = ?', whereArgs: [id]);
    return AdminModel.fromMap(amdinInfo.first);
  }

  static Future<int> insertBusInfo(BusModel busModel) async {
    print(busModel);
    final db = await open();
    return db.insert(busTableName, busModel.toMap());
  }

  static Future<int> insertSeatInfo(SeatModel seatModel) async {
    final db = await open();
    return db.insert(seatTableName, seatModel.toMap());
  }

  static Future<int> insertUserInfo(UserModel userModel) async {
    final db = await open();
    return await db.insert(userTableName, userModel.toMap());
  }

  static Future<List<SeatModel>> getAllSeats() async {
    final db = await open();
    final List<Map<String, dynamic>> seatList = await db.query(seatTableName);
    return List.generate(
        seatList.length, (index) => SeatModel.fromMap(seatList[index]));
  }

  static Future<int> updateBookedSeatNoAndIndex(String bookedSeatNo,
      String bookedSeatIndex, int busId) async {
    final db = await open();
    return db.update(
        seatTableName,
        {
          seatTableColSeatNo: bookedSeatNo,
          seatTableColSeatIndex: bookedSeatIndex
        },
        where: '$seatTableColSeatId = ?',
        whereArgs: [busId]);
  }

  static Future<int> updateAvailableSeat(int busId,
      String availableSeat) async {
    final db = await open();
    return db.update(
        busTableName, {busTableNameColAvailableSeat: availableSeat},
        where: '$busTableNameColId = ?', whereArgs: [busId]);
  }

  static Future<List<BusModel>> getBusBySearchItem(String from, String to,
      String date, String busType) async {
    final db = await open();
    final mapList = await db.query(busTableName,
        where:
        '$busTableNameColFrom = ? and $busTableNameColTo = ? and $busTableNameColDate = ? and $busTableNameColBusType = ?',
        whereArgs: [from, to, date, busType]);
    return List.generate(
        mapList.length, (index) => BusModel.fromMap(mapList[index]));
  }

  static Future<List<SeatModel>> getSingleSeatItem(int busId) async {
    final db = await open();
    final mapList = await await db.query(seatTableName,
        where: '$seatTableColBusId = ?', whereArgs: [busId]);
    return List.generate(
        mapList.length, (index) => SeatModel.fromMap(mapList.first));
  }

  static Future<List<BusModel>> getAllBus() async {
    final db = await open();
    final List<Map<String, dynamic>> busList = await db.query(busTableName);
    return List.generate(
        busList.length, (index) => BusModel.fromMap(busList[index]));
  }

  static Future<List<ReviewModel>> getAllReview() async {
    final db = await open();
    final List<Map<String, dynamic>> reviewList = await db.query(reviewTableName);
    return List.generate(
        reviewList.length, (index) => ReviewModel.fromMap(reviewList[index]));
  }

  static Future<List<UserModel>> getAllUser() async {
    final db = await open();
    final mapList = await db.query(userTableName);
    return List.generate(
        mapList.length, (index) => UserModel.fromMap(mapList[index]));
  }


  static Future<int> updateUserBookedSeatNoAndIndex(String bookedSeatNo,
      String bookedSeatIndex, int busId) async {
    final db = await open();
    return db.update(
        seatTableName,
        {
          seatTableColSeatNo: bookedSeatNo,
          seatTableColSeatIndex: bookedSeatIndex
        },
        where: '$seatTableColSeatId = ?',
        whereArgs: [busId]);
  }

  static Future<int> deleteUser(int id) async {
    final db = await open();
    return db.delete(
        userTableName, where: '$userTableColUserId = ?', whereArgs: [id]);
  }

  static Future<int> updateBusRating(int id, int index, String rating) async {
    final db = await open();
    return db.update(busTableName, {busTableNameColBusRating : rating},
        where: '$busTableNameColId = ?', whereArgs: [id]);
  }

  static Future<int> deleteUserbyBusId(int busId) async {
    final db = await open();
    return db.delete(
        userTableName, where: '$userTableColBusId = ?', whereArgs: [busId]);
  }

  static Future<int> deleteSeatbyBusId(int busId) async {
    final db = await open();
    return db.delete(
        seatTableName, where: '$seatTableColBusId = ?', whereArgs: [busId]);
  }

  static Future<int> deleteReviewbyBusId(int busId) async {
    final db = await open();
    return db.delete(
        reviewTableName, where: '$reviewTableColBusId = ?', whereArgs: [busId]);
  }

  static Future<int> deleteBusbyBusId(int busId) async {
    final db = await open();
    return db.delete(
        busTableName, where: '$busTableNameColId = ?', whereArgs: [busId]);
  }


  static Future<List<BusModel>> getBusByBusName(String busName) async {
    final db = await open();
    final mapList = await db.query(busTableName,
        where:
        '$busTableNameColName = ?',
        whereArgs: [busName]);
    return List.generate(
        mapList.length, (index) => BusModel.fromMap(mapList[index]));
  }



}
