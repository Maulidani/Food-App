<?php
namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Foods;

class FoodController extends Controller
{
    public function index(Request $request)
    {
        $search= $request->search;
        $product = Foods::orderBy('created_at', 'DESC')
        ->where('name', 'like', "%" . $search . "%")
        ->get();

        return response()->json([
            'message' => 'Success',
            'errors' => false,
            'data' => $product,
        ]);

    }
    public function indexCategory(Request $request)
    {
        $category= $request->category;
        $search= $request->search;
        $product = Foods::orderBy('created_at', 'DESC')
        ->where([
            ['category', '=', $category],
            ['name', 'like', "%" . $search . "%"]
        ])
        ->get();

        return response()->json([
            'message' => 'Success',
            'errors' => false,
            'data' => $product,
        ]);

    }

    // public function categoryProduct(Request $request)
    // {
    //     $product = ProductSeller::where('category', $request->category)->orderBy('created_at', 'DESC')->get;

    //     return response()->json([
    //         'message' => 'Success',
    //         'errors' => false,
    //         'product' => $product,
    //     ]);
    // }

    public function upload(Request $request)
    {

        $files = $request->image;
        $allowedfileExtension = ['jpeg', 'jpg', 'png', 'JPG', 'JPEG'];
        if ($request->hasfile('image')) {

            $filename = time() . '.' . $files->getClientOriginalName();
            $extension = $files->getClientOriginalExtension();

            $check = in_array($extension, $allowedfileExtension);

            if ($check) {

                $files->move(public_path() . '/app/image/food/', $filename);

                $upload = new Foods();
                $upload->id_admin_user = $request->id_admin_user;
                $upload->name = $request->name;
                $upload->category = $request->category;
                $upload->image = $filename;
                $upload->description = $request->description;
                $upload->recipe = $request->recipe;
                $upload->save();

                if ($upload) {

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

        $edit = Foods::find($request->id);
        $edit->name = $request->name;
        $edit->category = $request->category;
        $edit->description = $request->description;
        $edit->recipe = $request->recipe;
        $edit->save();

        if ($edit) {

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

                $files->move(public_path() . '/app/image/food/', $filename);
                $edit = Foods::find($request->id);
                $edit->image = $filename;
                $edit->save();
                if ($edit) {

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

    public function delete(Request $request)
    {
       $delete =  Foods::where(
            'id',
            $request->id
        )->delete();

        if($delete) {

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
}
