package zojae031.portfolio.presentation

interface BaseContract {
    interface View{
        fun showToast(text:String)

    }
    interface Presenter{
        fun onCreate()
    }
}