import 'package:flutter/material.dart';

class TicketCancelationPage extends StatefulWidget {
  static const String roureName = '/ticket_cancelation_page';

  const TicketCancelationPage({Key? key}) : super(key: key);

  @override
  State<TicketCancelationPage> createState() => _TicketCancelationPageState();
}

class _TicketCancelationPageState extends State<TicketCancelationPage> {
  final nameController = TextEditingController();
  final emailController = TextEditingController();
  final phoneController = TextEditingController();
  final nidController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Cancel Ticket'),
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(24.0),
          child: Column(
            children: [
              Text('Give us your information to cancel ticket', style: TextStyle(fontSize: 18, color: Colors.red),),
              SizedBox(height: 1,),
              Icon(Icons.cancel, size: 30, color: Colors.red,),
              SizedBox(height: 30,),
              TextField(
                controller: nameController,
                decoration: InputDecoration(
                  labelText: 'Enter name',
                  fillColor: Colors.grey[200],
                  filled: true,
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(20.0),
                    borderSide: BorderSide.none,
                  ),
                ),
              ),
              SizedBox(
                height: 20,
              ),
              TextField(
                controller: emailController,
                decoration: InputDecoration(
                  labelText: 'Enter Email',
                  fillColor: Colors.grey[200],
                  filled: true,
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(20.0),
                    borderSide: BorderSide.none,
                  ),
                ),
              ),
              SizedBox(
                height: 20,
              ),
              TextField(
                controller: phoneController,
                decoration: InputDecoration(
                  labelText: 'Enter Phone',
                  fillColor: Colors.grey[200],
                  filled: true,
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(20.0),
                    borderSide: BorderSide.none,
                  ),
                ),
              ),
              SizedBox(
                height: 20,
              ),
              TextField(
                controller: nidController,
                decoration: InputDecoration(
                  labelText: 'Enter NID number',
                  fillColor: Colors.grey[200],
                  filled: true,
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(20.0),
                    borderSide: BorderSide.none,
                  ),
                ),
              ),
              SizedBox(
                height: 50,
              ),
              ElevatedButton(
                  onPressed: () {
                    _cancelConfirmation();
                  },
                  child: Text("Confirm"),
                  style: ButtonStyle(
                    shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                        RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(30.0),
                        )),
                    padding:
                    MaterialStateProperty.resolveWith<EdgeInsetsGeometry>(
                            (Set<MaterialState> states) {
                          return EdgeInsets.only(
                              left: 90, right: 90, top: 15, bottom: 15);
                        }),
                    backgroundColor:
                    MaterialStateProperty.all<Color>(Colors.green),
                  )),
            ],
          ),
        ),
      ),
    );
  }

  void _cancelConfirmation () {

  }
}
