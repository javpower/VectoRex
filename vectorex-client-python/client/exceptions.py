class VectoRexError(Exception):
    """VectoRex 异常基类。"""
    pass

class LoginError(VectoRexError):
    """登录失败异常。"""
    pass

class RequestError(VectoRexError):
    """请求失败异常。"""
    pass