<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Users;

class UserController extends Controller
{
    public function register(Request $request)
    {
        $request->validate([
            'email' => 'required|email',
            'password' => 'required',
        ]);

        $files = $request->image;
        $allowedfileExtension = ['jpeg', 'jpg', 'png', 'JPG', 'JPEG'];
        if ($request->hasfile('image')) {

            $filename = time() . '.' . $files->getClientOriginalName();
            $extension = $files->getClientOriginalExtension();

            $check = in_array($extension, $allowedfileExtension);

            if ($check) {

                $files->move(public_path() . '/app/image/user/', $filename);

                $user = new Users();
                $user->name = $request->name;
                $user->image = $filename;
                $user->phone = $request->phone;
                $user->email = $request->email;
                $user->password = $request->password;
                $user->save();

                if ($user) {

                    return response()->json([
                        'message' => 'Success',
                        'errors' => false,
                    ]);
                } else {

                    return response()->json([
                        'message' => 'Fail',
                        'errors' => true,
                    ]);
                }
            } else {
                return response()->json([
                    'message' => 'Fail',
                    'errors' => true,
                ]);
            }
        } else {
            return response()->json([
                'message' => 'Fail',
                'errors' => true,
            ]);
        }
    }


    public function edit(Request $request)
    {
        $request->validate([
            'email' => 'required|email',
        ]);

        $user = Users::find($request->id);
        $user->name = $request->name;
        $user->phone = $request->phone;
        $user->email = $request->email;
        $user->password = $request->password;
        $user->save();

        if ($user) {

            return response()->json([
                'message' => 'Success',
                'errors' => false,
            ]);
        } else {

            return response()->json([
                'message' => 'Fail',
                'errors' => true,
            ]);
        }
    }

    public function editImage(Request $request)
    {
        $files = $request->image;
        $allowedfileExtension = ['jpeg', 'jpg', 'png', 'JPG', 'JPEG'];
        if ($request->hasfile('image')) {

            $filename = time() . '.' . $files->getClientOriginalName();
            $extension = $files->getClientOriginalExtension();

            $check = in_array($extension, $allowedfileExtension);

            if ($check) {

                $files->move(public_path() . '/app/image/user/', $filename);

                $user = Users::find($request->id);
                $user->image = $filename;
                $user->save();

                if ($user) {

                    return response()->json([
                        'message' => 'Success',
                        'errors' => false,
                    ]);
                } else {

                    return response()->json([
                        'message' => 'Fail',
                        'errors' => true,
                    ]);
                }

            } else {
                return response()->json([
                    'message' => 'Fail',
                    'errors' => true,
                ]);
            }
        } else {
            return response()->json([
                'message' => 'Fail',
                'errors' => true,
            ]);
        }
    }

    public function login(Request $request)
    {
        $email = $request->email;
        $password = $request->password;
        $type = $request->type;

        if ($type === 'user') {
            $exist = Users::where([
                ['email', '=', $email],
                ['password', '=', $password],
                ['type', '=', $type]
            ])->exists();

            if ($exist) {
                $data =  Users::where([
                    ['email', '=', $email],
                    ['password', '=', $password],
                    ['type', '=', $type]
                ])->first();

                return response()->json([
                    'message' => 'Success',
                    'errors' => false,
                    'data' => $data
                ]);
            } else {

                return response()->json([
                    'message' => 'Fail',
                    'errors' => true,
                ]);
            }

        } else if ($type === 'admin') {

            $exist = Users::where([
                ['email', '=', $email],
                ['password', '=', $password],
                ['type', '=', $type]
            ])->exists();

            if ($exist) {
                $data =  Users::where([
                    ['email', '=', $email],
                    ['password', '=', $password],
                    ['type', '=', $type]
                ])->first();

                return response()->json([
                    'message' => 'Success',
                    'errors' => false,
                    'data' => $data
                ]);
            } else {
                return response()->json([
                    'message' => 'Fail',
                    'errors' => true,
                ]);
            }
        } else {
            return response()->json([
                'message' => 'Fail',
                'errors' => true,
            ]);
        }
    }
}