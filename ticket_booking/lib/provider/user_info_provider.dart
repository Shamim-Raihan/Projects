import 'package:flutter/cupertino.dart';
import 'package:ticket_booking/db/database_helper.dart';
import 'package:ticket_booking/model/user_model.dart';

class UserInfoProvider extends ChangeNotifier {

  List<UserModel> userList = [];

  Future<bool> addNewUser(UserModel userModel) async {
    final rowId = await DBHelper.insertUserInfo(userModel);
    if(rowId > 0){
      userModel.userId = rowId;
      userList.add(userModel);
      notifyListeners();
      return true;
    }
    else {
      return false;
    }
  }

  getAllUser() async  {
    return await DBHelper.getAllUser().then((value){
      userList = value;
      notifyListeners();
    });
  }

  deleteUser(int id) async {
    final rowId = await DBHelper.deleteUser(id);
    if(rowId > 0){
      userList.removeWhere((element) => element.userId == id);
      notifyListeners();
    }
  }

  deleteUserByBusId(int busId) async {
    final rowId = await DBHelper.deleteUserbyBusId(busId);
    if(rowId > 0) {
      userList.removeWhere((element) => element.busId == busId);
      notifyListeners();
    }
  }

}