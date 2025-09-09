# :classical_building: Topic of the project:
Creating museum management system software.

### Project goal:
The aim of the project is to design and create software for a museum management system. The "MuseoMaster" project is an application created to streamline and facilitate museum management processes. This application is intended to support administrative staff and museum managers by providing tools and functions enabling efficient organization and control over the collection of exhibits, exhibitions and staff.

### Product functionalities:
The scope of the created product includes the creation of a mobile application and a database dedicated to it. Many users will be able to use the system at the same time. It will be a simple, intuitive system designed to facilitate the work of museum employees.

### For all users:

•	Login to the system

------------------------------------------------------------------------------------------------

### For administrative staff:
• Creating new users

• Deleting users

• Adding museum rooms

------------------------------------------------------------------------------------------------

### For museum curators:

• Creating exhibitions

• Adding new exhibits

• Removal of exhibits

• Editing exhibits

• Search for exhibits

• Assigning tasks related to exhibits to museum staff such as technical staff

------------------------------------------------------------------------------------------------

### A For exhibition technicians/security/museum guides/cleaning staff:

• View assigned tasks

• Confirmation of task completion

• Informing about the inability to complete the task

------------------------------------------------------------------------------------------------

For normal users

• See list of exhibits

• Read exhibits description

------------------------------------------------------------------------------------------------

### A Tools we used:

• Kotlin

• Java

• MySQLserver

------------------------------------------------------------------------------------------------

# User documentation

## Login

When we launch the application, the employee can log in to the created account.

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/95d6aea0-7c8c-47ac-b59c-2c584c361583)

------------------------------------------------------------------------------------------------

## Administrator panel

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/ddf6c36a-2355-4720-9aad-e712e2212fb0)

------------------------------------------------------------------------------------------------

Create user – a page that allows you to create a new user and specify his personal data

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/c476e9ca-f400-4a71-88e4-9ac60c66dcb2)

In the user tab, we can define things such as name, surname, email, phone number, age, role, password and permissions. The password must meet security requirements and the authorizations allow the employee to assign tasks to subordinates.

------------------------------------------------------------------------------------------------

In the add place tab, we can define the name of the new room, size and type of room

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/92e00936-6ff2-41d0-8ba2-a8841273ba4c)

------------------------------------------------------------------------------------------------

In the user list tab, we see a list of all users along with their key data. We can also delete a user if necessary

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/c6f05c97-5eda-4066-9337-cdea8b20b0d4)

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/7898c5a1-3260-4d00-863d-a0e5774d2953)

------------------------------------------------------------------------------------------------

The error reports tab shows a list of reports sent by users related to incorrect operation of the program.

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/1b9bcb8d-a372-4cd5-8d65-d37eb4804c10)

After clicking the report details button, a message with a note about the problem appears. Once the problem has been fixed, we can click delete report.

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/cdbb40ad-472b-41cb-82d4-b00956c36d9d)

------------------------------------------------------------------------------------------------

## Regular Employee Panel:

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/ea941458-50de-4468-826b-6d8408af5da7)

------------------------------------------------------------------------------------------------

The task list contains both a list of assigned tasks, thanks to which the employee can control the progress of his work.
After completing a task, the employee can mark the task as completed, completed with a problem or as not completed

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/dd36cc93-adb9-4583-b024-38a77ca58756)

After clicking on the content of the task, a window will open with a message from the superior.

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/c7ce176b-d841-47ca-8c0c-ade469972580)

Finished Task List

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/f61352fb-2b46-4444-9b57-92a6c62115f5)

Sending report to administrator

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/fccce519-86c4-48b3-8ea0-8820456f5659)

------------------------------------------------------------------------------------------------

## Employee+ panel (employee with permissions):

An employee+ user is very similar to a regular employee. The differences are:
- an additional list of assigned tasks thanks to which the manager can monitor the status of task completion
- assign a task page.

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/1d0d0582-7a5b-406a-8a67-bfe10748a12e)

------------------------------------------------------------------------------------------------

Assigned tasks

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/43f5b5b6-57b1-4f6c-9c40-7550f0b98ccc)

------------------------------------------------------------------------------------------------

Assign Task

The assign task option is an option available to all authorized users. Allows you to assign a task to a given employee. At the beginning, a window appears in which we can search for the employee to whom we want to assign a task. We can search for an employee by name and surname or username. We can also use the role selection field where we specify the role of the employee to whom we want to assign the task. After specifying the search parameters, click the Search button. After clicking, a list of searched employees will be displayed as below

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/91cfc219-e714-4243-bdc0-b69fbe6df1ff)

Once we have selected the employee to whom we want to assign a task, click the "Go to task details" button at the bottom of the page. After clicking it, a page with creating a task will be displayed, as shown in the image below.

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/b0473101-23dc-4eec-a3c2-da50d99250b7)

Now we can assign task or alternatively if we checked technician employee we can and assign task that involves exhibits

The next step is to search desired exhibits

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/5b2287c2-539a-484b-bae4-1bb74fd2098d)

Check desired exhibits and room to be moved, assign task

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/cb9b2968-b580-4fa7-aa76-8a990a6cb60b)

------------------------------------------------------------------------------------------------

## Curator's Panel

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/e29ef324-e1fc-4638-aef7-590d549dfa5b)

------------------------------------------------------------------------------------------------

Exhibition Creation

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/1150da13-4259-4fa0-87fe-24c2acb66825)

------------------------------------------------------------------------------------------------

Exhibition List

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/109ad059-cab5-42a7-86b0-ed07870e1982)

Exhibition details

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/a3c4e652-897c-4ba1-9913-1723f9d67b38)

------------------------------------------------------------------------------------------------

Exhibition Creation

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/0bd2d75f-7ee9-451d-8b66-23a28156410b)

------------------------------------------------------------------------------------------------

Add Exhibit

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/a9e26134-644e-4a62-8f2b-f28f76b836e5)

------------------------------------------------------------------------------------------------

Exhibit List

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/5f8aac71-a670-4660-92df-c6aa47fb2170)


Exhibit details, where we can delete or edit exhibit

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/5f1ed5df-3299-4188-a969-5c67cd9e3894)

Exhibit edit

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/35d67379-5d0b-4403-a9a2-3348e9754331)

Exhibit search

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/be1e67b4-6ac3-40c2-a2ad-08051bfc690e)


Searched exhibits

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/39fac02a-8010-41e4-a40f-a935558a513a)

------------------------------------------------------------------------------------------------

## Technician Panel

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/77ff6d58-e677-4320-9c5f-f27f0d77a92f)

------------------------------------------------------------------------------------------------

Technician have exhibits list that represents exhibits from assigned tasks that sould be moved 

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/55c58b65-1717-412f-b78a-38faaae63494)

after clicking "confirm" exhibits change their places

------------------------------------------------------------------------------------------------

## Technician+ Panel
It is the same as technician but have permission to assign tasks

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/d5bb542f-fcff-40e1-a4ec-870b310e8b9d)

------------------------------------------------------------------------------------------------

## Normal user creation

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/45d44f6c-2dad-40ae-9f43-39a58eaf3bdf)

After clicking create account normal user can create its account

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/fc2e7670-9c12-4f6c-a920-45a4e02b051c)

------------------------------------------------------------------------------------------------

## Normal user Panel

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/a33580a1-a878-43d9-8279-2d0778f9840a)

Normal user can check exhibits in Museum 

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/ffebf092-13e5-4025-a5b7-120f2a0ad785)

Details

![image](https://github.com/LukaszKrolicki/MuseoMaster-Mobile/assets/54467678/1c3bfa9a-27c3-4ac4-9c2a-355c9eb80fdf)




