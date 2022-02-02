<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Factories\HasFactory;

class Foods extends Model
{
    use HasFactory;
    public $timestamps = false;

    protected $table = 'foods';

    protected $fillable = [
       'id', 'id_admin_user', 'name', 'image','description','recipe',
    ];

}