import 'package:shared_preferences/shared_preferences.dart';

Future<bool> setSession(bool status) async {
  final preference = await SharedPreferences.getInstance();
  return preference.setBool('isLogedIn', status);
}

Future<bool> getSession() async {
  final preference = await SharedPreferences.getInstance();
  return preference.getBool('isLogedIn') ?? false;
}