import 'package:flutter/cupertino.dart';
import 'package:ticket_booking/db/database_helper.dart';
import 'package:ticket_booking/model/seat_model.dart';

class SeatInfoProvider extends ChangeNotifier {

  List<SeatModel> seatList = [];
  List<SeatModel> singleSeat = [];

  getAllSeat() {
    DBHelper.getAllSeats().then((value) {
      seatList = value;
      notifyListeners();
    });
  }

  Future<bool> addNewSeat(SeatModel seatModel) async {
    final rowId = await DBHelper.insertSeatInfo(seatModel);
    if(rowId > 0){
      seatModel.seatId = rowId;
      seatList.add(seatModel);
      notifyListeners();
      return true;
    }
    else {
      return false;
    }
  }

  updateBookedSeatAndIndex(String bookedSeatNo, String bookedSeatIndex, int busId, int index) {
    DBHelper.updateBookedSeatNoAndIndex(bookedSeatNo, bookedSeatIndex, busId).then((value) {
      seatList[index].seatNo = bookedSeatNo;
      seatList[index].seatIndex = bookedSeatIndex;
      notifyListeners();
    });
  }

  Future<List<SeatModel>> getSingleBusSeat(int busId){
    return DBHelper.getSingleSeatItem(busId);
  }


  deleteSeatByBusId(int busId) async {
    final rowId = await DBHelper.deleteSeatbyBusId(busId);
    if(rowId > 0) {
      seatList.removeWhere((element) => element.busId == busId);
      notifyListeners();
    }
  }


}