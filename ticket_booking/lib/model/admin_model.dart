class AdminModel {
  int? id;
  String email;
  String password;

  AdminModel({this.id, required this.email, required this.password});

  Map<String, dynamic> toMap() {
    var map = <String, dynamic>{
      adminTableNameColEmail: email,
      adminTableNameColpassword: password,
    };
    if (id != null) {
      map[adminTableNameColId] = id;
    }
    return map;
  }

  factory AdminModel.fromMap(Map<String, dynamic> map) => AdminModel(
      email: map[adminTableNameColEmail],
      password: map[adminTableNameColpassword]);

}

const String adminTableName = 'admin_table';

const String adminTableNameColId = 'id';
const String adminTableNameColEmail = 'email';
const String adminTableNameColpassword = 'password';

