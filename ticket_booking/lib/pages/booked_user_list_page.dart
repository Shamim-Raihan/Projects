import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:provider/provider.dart';
import 'package:ticket_booking/model/add_new_bus_model.dart';
import 'package:ticket_booking/model/user_model.dart';
import 'package:ticket_booking/pages/user_details_page.dart';
import 'package:ticket_booking/provider/user_info_provider.dart';

class BookedUserListPage extends StatefulWidget {
  static const String roureName = '/booked_user_list';
  const BookedUserListPage({Key? key}) : super(key: key);

  @override
  State<BookedUserListPage> createState() => _BookedUserListPageState();
}

class _BookedUserListPageState extends State<BookedUserListPage> {

  late UserInfoProvider userInfoProvider;
  List<UserModel> userList  = [];
  List<UserModel> newUserList  = [];
  late int busId;

  @override
  void didChangeDependencies() {
    busId = ModalRoute.of(context)!.settings.arguments as int;
    userInfoProvider = Provider.of<UserInfoProvider>(context);
    userList = userInfoProvider.userList;
    for(int i = 0; i < userList.length; i++){
      if(busId == userList[i].busId){
        newUserList.add(userList[i]);
      }
    }
    print('new list ${newUserList.length}');
    super.didChangeDependencies();
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('All user list'),),
      body: newUserList.length <= 0 ? Center(child: Text('No user Found',style: TextStyle(fontSize: 25),)) : Consumer<UserInfoProvider>(
        builder: (context, provider, _) => ListView.builder(
          padding: EdgeInsets.all(8),
          itemCount: newUserList.length,
          itemBuilder: (context, index){
            return Card(
                elevation: 5,
                child: ListTile(
                  contentPadding: EdgeInsets.all(10),
                  title: Text('Name : ' + newUserList[index].name, style: TextStyle(fontSize: 20,color: Colors.black),),
                  subtitle: Text('Contact Number : ' + newUserList[index].phone),
                  trailing: TextButton(
                    onPressed: () {
                      Navigator.pushNamed(context, UserDetailsPage.roureName, arguments: newUserList[index]);
                    },
                    child: Text('View Details'),
                  ),
                  leading: Icon(Icons.person, size: 50,color: Colors.black,),
                  tileColor: index.isEven ? Colors.grey[100] : Colors.grey[300],
                  horizontalTitleGap: 0,
                ),

            );
          },
        ),
      )
    );
  }
}
