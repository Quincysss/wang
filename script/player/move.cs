using UnityEngine;
using UnityEditor;

public class move : MonoBehaviour
{
    public float forward = 1000f;
    public float right = 500f;
    public Rigidbody rb;
    private float speed = 1;
    float time;
    Animator animator;
    public bool goright;
    public bool goleft;
    public score timescore;
    public collison timer;
    public Camera camera;
    private bool flag;

    void Start()
    {
        animator = GetComponentInChildren<Animator>();
        flag = false;
    }

    void FixedUpdate()
    {
        time = timer.getTimer();
        int hour = (int)time / 3600;
        int min = (int)((int)time - hour * 3600) / 60;
        int second = (int)((int)time - hour * 3600 - min * 60);
        if (second > 3)
        {
            float number = (float)(1.0 / 40) * time;
            speed = 1 + Mathf.Pow(number, 2);
        }
        else
        {
            speed = 1;
        }
        if (transform.position.x < -11 || transform.position.x > 11)
        {
            gravity();
        }
        transform.Translate(new Vector3(0, 0, forward * speed * Time.deltaTime));
        if (Input.GetMouseButtonDown(0))
        {
            flag = true;
        }
        if (Input.GetMouseButtonUp(0))
        {
            flag = false;
        }
        if (flag)
        {
            Ray ray = camera.ScreenPointToRay(Input.mousePosition);
            Plane plane = new Plane(Vector3.forward, transform.position);
            float dist = 0;
            if (plane.Raycast(ray, out dist))
            {
                Vector3 pos = ray.GetPoint(dist);
                Vector3 now = new Vector3(pos.x, transform.position.y, transform.position.z);
                transform.position = Vector3.MoveTowards(transform.position, now, right * speed * Time.deltaTime);
            }
        }
        /*if (goright)
        {
            transform.Translate(new Vector3(right * speed * Time.deltaTime, 0, 0));
        }
        if (goleft)
        {
            transform.Translate(new Vector3(-right * speed * Time.deltaTime, 0, 0));
        }
        */
    }

    public void gravity()
    {
        rb.useGravity = true;
    }
}
