import 'package:flutter/material.dart';
import 'package:ticket_booking/db/database_helper.dart';
import 'package:ticket_booking/model/admin_model.dart';

class AdminInfoProvider extends ChangeNotifier{

  List<AdminModel> adminList = [];
  late AdminModel adminModel;


  Future<bool> addNewAdmin(AdminModel adminModel) async {
    final rowId = await DBHelper.insertAdmin(adminModel);
    if(rowId > 0){
      adminModel.id = rowId;
      notifyListeners();
      return true;
    }
    else {
      return false;
    }
  }

  Future<AdminModel> getAdminById(int id) => DBHelper.getAdminInfo(id);
}