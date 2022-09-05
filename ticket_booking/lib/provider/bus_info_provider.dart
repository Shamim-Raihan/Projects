import 'package:flutter/material.dart';
import 'package:ticket_booking/db/database_helper.dart';
import 'package:ticket_booking/model/add_new_bus_model.dart';

class BusInfoProvider extends ChangeNotifier{

  List<BusModel> busList = [];

  late BusModel busModel;

  Future<bool> addNewBus(BusModel busModel) async {
    final rowId = await DBHelper.insertBusInfo(busModel);
    if(rowId > 0){
      busModel.id = rowId;
      busList.add(busModel);
      notifyListeners();
      return true;
    }
    else {
      return false;
    }
  }

  Future<List<BusModel>> getBusBySearchItems(String from, String to, String date, String busType){
    return DBHelper.getBusBySearchItem(from, to, date, busType);
  }

  updateAvailableSeat(int busId, String availableSeat, int index) {
    DBHelper.updateAvailableSeat(busId, availableSeat).then((value){
      busList[index].availableSeat = availableSeat;
      notifyListeners();
    });
  }

  getAllBus(){
    DBHelper.getAllBus().then((value) {
      busList = value;
      notifyListeners();
    });
  }

  updateRating(int id, int index, String rating){
    DBHelper.updateBusRating(id, index, rating).then((value) {
      busList[index].rating = rating;
      notifyListeners();
    });
  }

  deleteBusByBusId(int busId) async {
    final rowId = await DBHelper.deleteBusbyBusId(busId);
    if(rowId > 0) {
      busList.removeWhere((element) => element.id == busId);
      notifyListeners();
    }
  }


  Future<List<BusModel>> getBusByBusName(String busName){
    return DBHelper.getBusByBusName(busName);
  }


}


















