package uz.mahmudxon.picsumapplication

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import uz.mahmudxon.picsumapplication.adapter.ListAdapter
import uz.mahmudxon.picsumapplication.model.PicsumData
import uz.mahmudxon.picsumapplication.network.PicsumApi
import uz.mahmudxon.picsumapplication.network.PicsumClient
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import uz.mahmudxon.picsumapplication.fragment.ShowInfoFragment


class MainActivity : AppCompatActivity() {
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var adapter: ListAdapter
    private lateinit var myApi : PicsumApi
    val MAX_PAGE = 33
    var CURRENT_PAGE = 1
    private var allData = ArrayList<PicsumData>()
    private val PermissionsRequestCode = 123
   // private lateinit var managePermissions: ManagePermissions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = listOf<String>(
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
     //   managePermissions = ManagePermissions(this,list,PermissionsRequestCode)
        initView()

        initClient()

        fetchData()
    }

    private fun initClient() {
        val retrofit = PicsumClient().getRetrofitData()
        myApi = retrofit.create(PicsumApi::class.java)
    }

    private fun fetchData(){
        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(myApi.getPhotoByPage(CURRENT_PAGE, 30)
            .subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe( {
                allData.addAll(it)
                displayData(it)
                Log.i("myLog", "${it.size}")
            },
                {
                    Log.i("myLog", "${it.message}")

        })
        )
    }

    private fun displayData(data : ArrayList<PicsumData>)
    {
        runOnUiThread {
            adapter.adddata(data)
        }
    }
    private fun initView()
    {
        list.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter = ListAdapter(this, ArrayList())
        list.adapter = adapter
        list.setHasFixedSize(false)
        adapter.setOnClickListener {
            startFragment(it, allData)
        }
        list.addOnScrollListener(object : RecyclerView.OnScrollListener()
        {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!list.canScrollVertically(1) && allData.size > 0)
                {
                    CURRENT_PAGE ++
                    if (CURRENT_PAGE >MAX_PAGE) return
                    fetchData()
                }
            }
        })
    }

    private fun startFragment(pos : Int, allData: ArrayList<PicsumData>) {
        val f = ShowInfoFragment()
        f.pos = pos
        f.allData = allData
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, f).addToBackStack("myFragment").commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 0)
        {
            supportFragmentManager.popBackStackImmediate()
            return
        }
        super.onBackPressed()
    }
}

/*
class ManagePermissions(val activity: Activity, val list: List<String>, val code:Int) {

    // Check permissions at runtime
    fun checkPermissions() {
        if (isPermissionsGranted() != PackageManager.PERMISSION_GRANTED) {
            showAlert()
        } else {
            activity.toast("Permissions already granted.")
        }
    }

*/
/*

    // Check permissions status
    private fun isPermissionsGranted(): Int {
        // PERMISSION_GRANTED : Constant Value: 0
        // PERMISSION_DENIED : Constant Value: -1
        var counter = 0;
        for (permission in list) {
            counter += ContextCompat.checkSelfPermission(activity, permission)
        }
        return counter
    }


    // Find the first denied permission
    private fun deniedPermission(): String {
        for (permission in list) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                == PackageManager.PERMISSION_DENIED) return permission
        }
        return ""
    }


    // Show alert dialog to request permissions
    private fun showAlert() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Need permission(s)")
        builder.setMessage("Some permissions are required to do the task.")
        builder.setPositiveButton("OK", { dialog, which -> requestPermissions() })
        builder.setNeutralButton("Cancel", null)
        val dialog = builder.create()
        dialog.show()
    }


    // Request the permissions at run time
    private fun requestPermissions() {
        val permission = deniedPermission()
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            // Show an explanation asynchronously
            activity.toast("Should show an explanation.")
        } else {
            ActivityCompat.requestPermissions(activity, list.toTypedArray(), code)
        }
    }


    // Process permissions result
    fun processPermissionsResult(requestCode: Int, permissions: Array<String>,
                                 grantResults: IntArray): Boolean {
        var result = 0
        if (grantResults.isNotEmpty()) {
            for (item in grantResults) {
                result += item
            }
        }
        if (result == PackageManager.PERMISSION_GRANTED) return true
        return false
    }
}
// Extension function to show toast message
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}*/
