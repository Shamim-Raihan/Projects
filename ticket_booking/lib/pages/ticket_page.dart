import 'package:flutter/material.dart';
import 'package:ticket_booking/pages/home_page.dart';
import 'package:ticket_booking/pages/user_info_page.dart';

class TicketPage extends StatefulWidget {
  static const String roureName = '/ticket_page';
  const TicketPage({Key? key}) : super(key: key);

  @override
  State<TicketPage> createState() => _TicketPageState();
}

class _TicketPageState extends State<TicketPage> {

  late ScreenArgument2 args;

  @override
  void didChangeDependencies() {
    args = ModalRoute.of(context)!.settings.arguments as ScreenArgument2;
    super.didChangeDependencies();
  }



  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Ticket'),),
      body: SafeArea(
        child: Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              // const Text(' TICKET', style: TextStyle(fontSize: 30, fontFamily: 'roboto', fontWeight: FontWeight.w400),),
              const SizedBox(height: 20,),
              Container(
                height: 400,
                width: 380,
                padding: const EdgeInsets.only(left: 5, right: 5),
                decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(15),
                    color: Colors.grey[300],
                    boxShadow:const [
                      BoxShadow(
                        blurRadius: 15.0,
                        offset: Offset(4.0,4.0),
                        spreadRadius: 1.0,
                      ),
                    ]
                ),
                child: Column(
                    children: [
                      const SizedBox(height: 15,),
                      Text('Bus: ${args.busModel.busName}' ,style: const TextStyle(fontWeight: FontWeight.bold,fontSize: 25,fontFamily: 'Trajan Pro'),),
                      Text('Bus Number: ${args.busModel.busUniqueNo}',style: const TextStyle(fontSize: 20),),
                      Text('Seat Number: ${args.seatNoList}',style: const TextStyle(fontSize: 20),),
                      const SizedBox(height: 20,),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text('From: ${args.busModel.from}',style: const TextStyle(fontSize: 17),),
                              Text('To: ${args.busModel.to}',style: const TextStyle(fontSize: 17),),
                              Text('Time: ${args.busModel.time}',style: const TextStyle(fontSize: 17),),
                              Text('Name: ${args.userModel.name}',style: const TextStyle(fontSize: 17),),
                              Text('Phone: ${args.userModel.phone}',style: const TextStyle(fontSize: 17),),
                              Text('Email ${args.userModel.email}:',style: const TextStyle(fontSize:17),),
                            ],
                          ),
                        ],
                      ),
                      const SizedBox(height: 30,),
                      Text('NID: ${args.userModel.nid}',style: const TextStyle(fontSize: 17),),
                      const SizedBox(height: 30,),
                      Column(
                        children: const [
                          Text('Payment: Clear',style: TextStyle(fontSize: 18,color: Colors.green,fontWeight: FontWeight.bold),)
                        ],
                      )
                    ]
                ),
              ),

              SizedBox(height: 30,),
              TextButton(onPressed: (){
                Navigator.pushReplacementNamed(context, HomePage.roureName);
              }, child: const Text('Click Here to Continue')),

            ],
          ),
        ),
      ),
    );
  }
}
